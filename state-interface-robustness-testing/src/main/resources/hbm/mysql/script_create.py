import sys


files = [
    "Environment",
    "Fault",
    "Message",
    "MessageArgument",
    "MessageDevice",
    "State",
    "TestCase",
    "TestInput",
    "TestInputArgument",
    "TestMessage",
    "TestSpecs",
    "TestState",
    "TestStateOutput",
    "MutantMessageArgument",
    "MutantTestCase",
    "TestExecutionContext"
]
print("-- CREATE USER 'test'@'localhost' IDENTIFIED BY 'test-321';")
print("-- CREATE USER 'test'@'%' IDENTIFIED BY 'test-321';")
print("-- CREATE DATABASE test;")
print("-- GRANT ALL PRIVILEGES ON test.* TO 'test'@'localhost';")
print("-- GRANT ALL PRIVILEGES ON test.* TO 'test'@'%';")
print('-- use test;')
print()
for f in files:
    with open('%s.sql.ddl' % f, 'r') as fp:
        lines = fp.readlines()
        print("-- FILE: %s.sql.ddl" % f)
        print("".join(lines))
        print()