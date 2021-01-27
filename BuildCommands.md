# Building and Maintaining Project

This is a project template for AEM-based applications. It is intended as a best-practice set of examples as well as a potential starting point to develop your own functionality.

## Versioning
Use `versions:set` from the versions-maven plugin:

`mvn versions:set -DnewVersion=2.50.1-SNAPSHOT -DprocessAllModules`
It will adjust all pom versions, parent versions and dependency versions in a multi-module project.

If you made a mistake, do `mvn versions:revert` afterwards, or `mvn versions:commit -DprocessAllModules`
