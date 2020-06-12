import sys


class Builder(object):
    def __init__(self):
        self._string_builder = ''

    def append(self, text=''):
        self._string_builder += text

    def appendln(self, text=''):
        self.append('%s\n' % text)

    def build(self):
        raise Exception('Not implemented')

    def get_lines(self):
        for line in self._string_builder.splitlines():
            yield line


class CommandLineBuilder(Builder):
    def __init__(self):
        Builder.__init__(self)

    def build(self):
        for line in self.get_lines():
            print(line)


class FileBuilder(Builder):
    def __init__(self, filename):
        Builder.__init__(self)
        self._filename = filename

    def build(self):
        for line in self.get_lines():
            with open(self._filename, 'a') as f:
                f.write('%s\n' % line)
        print('File Saved!')


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
    "TestCaseFault",
    "MessageArgumentFault",
    "TestExecutionContext",
    "TestPlan",
    "RabbitQueue",
    "RabbitExchange",
    "RabbitBind",
    "RabbitTestBind"
]


def fill(builder, path='./'):
    builder.appendln("-- CREATE USER 'test'@'localhost' IDENTIFIED BY 'test-321';")
    builder.appendln("-- CREATE USER 'test'@'%' IDENTIFIED BY 'test-321';")
    builder.appendln("-- DROP DATABASE IF EXISTS test;")
    builder.appendln("-- CREATE DATABASE test;")
    builder.appendln("-- GRANT ALL PRIVILEGES ON test.* TO 'test'@'localhost';")
    builder.appendln("-- GRANT ALL PRIVILEGES ON test.* TO 'test'@'%';")
    builder.appendln('-- USE test;')
    builder.appendln()
    for f in files:
        with open('%s%s.sql.ddl' % (path, f), 'r') as fp:
            lines = fp.readlines()
            builder.appendln("-- FILE: %s.sql.ddl" % f)
            builder.appendln("".join(lines))
            builder.appendln()


if __name__ == '__main__':
    builder = CommandLineBuilder()
    fill(builder)
    builder.build()