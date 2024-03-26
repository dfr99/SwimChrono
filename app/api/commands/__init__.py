"""Auxiliar module to run CI commands."""
import subprocess as sp
from pathlib import Path
from shutil import which


def lint():
    if which("black"):
        sp.check_call("black --check .", shell=True)
    else:
        _missing_command("black")


def format():
    if which("black"):
        sp.check_call("black .", shell=True)
    else:
        _missing_command("black")
    

def _missing_command(command):
    print("====| ERROR: WRONG COMMAND: " + command)
