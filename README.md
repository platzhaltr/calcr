# README #

**calcr** is a small calculator that interprets text as a mathematical expression, supporting basic operators (`+`,`-`,`*`,`/`) and variable substitution.

```java
float result = Calcr.parseFloat("1 + x", "x", 42); // result = 43
```

Or use multiple variables

```java
Map<String, Float> variables = new HashMap<String, Float>();
variables.put("x", 42f);
variables.put("y", 7f);
float result = Calcr.parseFloat("x / y", variables); // result = 6
```

## Development ##

	git clone git://github.com/platzhaltr/calcr.git
	cd calcr
	mvn clean install

	