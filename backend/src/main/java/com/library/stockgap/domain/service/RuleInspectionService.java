package com.library.stockgap.domain.service;
import org.kie.dmn.feel.FEEL;
import org.kie.dmn.feel.lang.ast.ASTNode;
import org.kie.dmn.feel.lang.types.BuiltInType;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;
import java.util.*;

@Service
public class RuleInspectionService {
    private final RuleValidationPolicy policy;
    public RuleInspectionService(RuleValidationPolicy policy) { this.policy=policy; }
    public List<Map<String,Object>> inspect(String xml, Map<String,Object> facts) {
        policy.validate(xml);
        try {
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();factory.setNamespaceAware(true);
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl",true);
            factory.setFeature("http://xml.org/sax/features/external-general-entities",false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities",false);
            var document=factory.newDocumentBuilder().parse(new InputSource(new StringReader(xml)));
            var inputs=document.getElementsByTagNameNS("*","input");var entries=document.getElementsByTagNameNS("*","inputEntry");
            FEEL feel=FEEL.newInstance(getClass().getClassLoader(),List.of());var context=feel.newCompilerContext();context.setDoCompile(false);
            policy.factTypes().forEach((name,type)->context.addInputVariableType(name,type.equals("boolean")?BuiltInType.BOOLEAN:type.equals("number")?BuiltInType.NUMBER:BuiltInType.STRING));
            List<Map<String,Object>> conditions=new ArrayList<>();
            for(int index=0;index<inputs.getLength();index++) {
                Element input=(Element)inputs.item(index);
                String expression=input.getElementsByTagNameNS("*","text").item(0).getTextContent();
                String expected=((Element)entries.item(index)).getElementsByTagNameNS("*","text").item(0).getTextContent().trim();
                Set<String> names=new TreeSet<>();collect(feel.processExpression(expression,context).getInterpreted().getASTNode(),names);
                Map<String,Object> values=new LinkedHashMap<>();for(String name:names)values.put(name,facts==null?null:facts.get(name));
                String result="NOT_TESTED";
                if(facts!=null) {
                    if(values.values().stream().anyMatch(Objects::isNull))result="UNKNOWN";
                    else {
                        Object value=feel.evaluate(expression,facts);
                        Object match=expected.equals("-")?true:expected.startsWith("<")||expected.startsWith(">")||expected.startsWith("!=")?
                            feel.evaluate("("+expression+") "+expected,facts):Objects.equals(value,feel.evaluate(expected));
                        result=match==null?"UNKNOWN":Boolean.TRUE.equals(match)?"TRUE":"FALSE";
                    }
                }
                conditions.add(Map.of("question",input.getAttribute("label").isBlank()?expression:input.getAttribute("label"),"expression",expression,"expected",expected,"result",result,"facts",values));
            }
            return conditions;
        } catch(Exception error){throw new IllegalArgumentException("Essai de la règle impossible",error);}
    }
    private void collect(ASTNode node,Set<String> names) {
        if(node.getClass().getSimpleName().equals("NameRefNode"))names.add(node.getText());
        for(ASTNode child:node.getChildrenNode())collect(child,names);
    }
}