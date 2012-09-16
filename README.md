# README #

**calcr** is a small calculator that interprets text as a mathematical expression, supporting basic operators (`+`,`-`,`*`,`/`) and variable substitution.

	float result = Calcr.parseFloat("1 + x", "x", 42); // result = 6

Or use multiple variables

	Map<String, Float> variables = new HashMap<String, Float>();
	variables.put("x", 42f);
	variables.put("y", 7f);
	float result = Calcr.parseFloat("1 + x / y", variables); // result = 7
	
## Development ##	 

Aa working version of [lingwah](http://code.google.com/p/lingwah/) is needed. I took the liberty of creating a mavenized version and fix a very small bug (in a test case).

	git clone git://github.com/oschrenk/lingwah.git
	cd lingwah
	mvn clean install

After that you can install **calcr**

	git clone git://github.com/platzhaltr/calcr.git
	cd calcr
	mvn clean install

	