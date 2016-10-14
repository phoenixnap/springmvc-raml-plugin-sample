![PhoenixNAP Logo](https://phoenixnap.com/wp-content/themes/phoenixnap-v2/img/v2/logo.svg)
## Spring MVC-RAML Synchronizer Sample [![Apache License 2](https://img.shields.io/badge/license-ASF2-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.txt)

The Spring MVC-RAML Synchronizer Sample is an implementation of the Spring MVC-RAML plugin available [here](https://github.com/phoenixnap/springmvc-raml-plugin) .

All generated code originates from a RAML doc. The rest of the code extends the generated code to form a working sample.

The sample provided is of a simple 'Drinks Service', offering a basic CRUD to in-memory resources in the form of 'Drinks'.

# Building from Source
The SpringMVC-RAML Plugin Sample uses a [Maven][]-based build system.

Building the project requires a simple maven build.

In the command line type:
```
mvn clean install
```

Optionally, if you want `org.springframework.cloud.netflix.feign.FeignClient` as your client - you need to specify `feign-client` as maven profile:
```
mvn clean install -Pfeign-client
```

## Prerequisites
[Git][] and [JDK 8 update 20 or later][JDK8 build]

Be sure that your `JAVA_HOME` environment variable points to the `jdk1.8.0` folder
extracted from the JDK download.

##Customising Configuration
The configuration of the plugin is based on a number of properties specified within the parent and child POMs.

###The parent POM

The following properties are defined in the parent POM:

```
<plugin-version>...</plugin-version>r
<output-relative-path>...</output-relative-path>		
```

- `<plugin-version>` defines the version of the raml plugin being used.
- `<output-relative-path>` defines the outermost directory used for code generation. We use `/target/generated-raml-mvc`

###The Child POMs

The following properties are defined in the server and client POMs:

```
<raml-path>...</raml-path>
<base-package>...</base-package>		
```

- `<raml-path>` describes the relative path to the raml file
- `<base-package>` defines the base package for the generated files e.g. `com.phoenixnap.oss.server`

####The Client POM

The client POM additionally defines the following parameter:

```
<url-field-annotation-path>...</url-field-annotation-path>
```

When the client is generated the following code is also generated with it:

```
@Value("${drinks-server.url}")
private String baseUrl;
```

The `@Value` annotation value `drinks-server.url` is a configurable string configured by the `url-field-annotation-path` described above.

In case of `FeignClient`generation, url is defined using `<baseUri>` in client POM and the generated code looks like this:

```
@FeignClient(url = "${drinks-server.url}/drinks", name = "drinkClient")
```

## Documentation & Getting Support
Usage and documentation are available in the Javadoc and README.md of the child projects. Kindly contact the developers via email (available in the pom files) if required or open an [Issue][] in our tracking system.

## License
The SpringMVC-RAML Plugin Sample is released under version 2.0 of the [Apache License][].

## Contributing
[Pull requests][] are welcome; Be a good citizen and create unit tests for any bugs squished or features added

#Final Note
This project is created also to provide a baseline to ensure the client and controller generation function in harmony. The project provides end to end tests which use the generated code to set up a server and client and ascertain that all code generated performs matching requests and responses. **This should not be broken**.

[Pull requests]: http://help.github.com/send-pull-requests
[Apache License]: http://www.apache.org/licenses/LICENSE-2.0
[Git]: http://help.github.com/set-up-git-redirect
[JDK8 build]: http://www.oracle.com/technetwork/java/javase/downloads
[Maven]: http://maven.apache.org/
[Issue]: https://github.com/phoenixnap/springmvc-raml-plugin-sample/issues
