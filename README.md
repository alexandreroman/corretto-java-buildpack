# Amazon Correto Java Buildpack

This [Spring Boot](https://spring.io/projects/spring-boot) app is a demo project showcasing
[Amazon Corretto JRE](https://docs.aws.amazon.com/corretto/latest/corretto-8-ug/what-is-corretto-8.html)
, deployed to [Cloud Foundry](https://www.cloudfoundry.org).

The app is actually reusing the
[Cloud Foundry Java Buildpack](https://github.com/cloudfoundry/java-buildpack):
we configure the buildpack to replace OpenJDK with Amazon Corretto JRE.

## How to use it?

Just compile this project with JDK 8, and deploy it to Cloud Foundry:
```shell
$ ./mvnw clean package && cf push
```

This app is exposing a single page, where JVM system properties are shown.
It is also using [Spring Data](https://spring.io/projects/spring-data)
to check Amazon Corretto compatibility with persistence libraries and JDBC drivers.

If you want to use Amazon Corretto with your app, include this configuration
in your Cloud Foundry app manifest:
```yml
  buildpacks:
  # We are actually reusing the Cloud Foundry Java Buildpack,
  # since Amazon Corretto is an OpenJDK build.
  - https://github.com/cloudfoundry/java-buildpack.git
  env:
    # Tell Java Buildpack to use a custom repository,
    # where Amazon Corretto is published.
    JBP_CONFIG_OPEN_JDK_JRE: '{ jre: { repository_root: "https://s3.eu-west-3.amazonaws.com/corretto-repo-demo/corretto-jre" } }'
```

## Contribute

Contributions are always welcome!

Feel free to open issues & send PR.

## License

Copyright &copy; 2018 Pivotal Software, Inc.

This project is licensed under the [Apache Software License version 2.0](https://www.apache.org/licenses/LICENSE-2.0).
