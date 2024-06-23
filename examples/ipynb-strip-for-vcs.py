#!/usr/bin/env python3

'''
A script used as a Git filter to exclude output when placing Jupyter notebooks under version control.
Inspired by this Gist: https://gist.github.com/pbugnion/ea2797393033b54674af
'''

import sys
import json

nb = sys.stdin.read()
json_in = json.loads(nb)

def strip_output_from_cell(cell):
    if "outputs" in cell:
        cell["outputs"] = []
    if "execution_count" in cell:
        cell["execution_count"] = None

for cell in json_in["cells"]:
    strip_output_from_cell(cell)

json.dump(json_in, sys.stdout, sort_keys=True, indent=1, separators=(",",": "))
