"""Contrôle structurel de la seule règle bootstrap, sans LLM."""
from pathlib import Path
import unittest
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]
DMN = ROOT / 'rules' / 'R001-v1.dmn'


class BootstrapRuleContract(unittest.TestCase):
    def test_bootstrap_exists_and_contains_one_rule(self):
        self.assertTrue(DMN.exists(), 'Le DMN bootstrap doit être livré')
        root = ET.parse(DMN).getroot()
        ns = {'d': root.tag.split('}')[0][1:]}
        self.assertEqual(len(root.findall('.//d:decisionTable', ns)), 1)
        self.assertEqual(len(root.findall('.//d:rule', ns)), 1)
        self.assertEqual(root.find('.//d:decisionTable', ns).get('hitPolicy'), 'COLLECT')
        self.assertEqual(root.findall('.//d:import', ns), [])


if __name__ == '__main__':
    unittest.main()
