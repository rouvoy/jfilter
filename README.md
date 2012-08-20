# Java Filter Library

This is a simple library to match POJO against LDAP-like or JSON-like filters.

## Maven compilation

JFilter is a [Maven](http://maven.apache.org "Maven") managed project. All you have to do is to invoke the `install` command from the root directory (`JFILTER_DIR`):

``` bash
cd $JFILTER_DIR
mvn install
```


## Maven artefact

Once installed, the JFilter library artefact is available as:

``` xml
<dependency>
    <groupId>fr.inria.jfilter</groupId>
    <artifactId>jfilter-library</artifactId>
    <version>1.1-SNAPSHOT</version>
</dependency>
```

## API usage

``` java
public class Person {
    String firstname = "John", name = "Doe";
	int age = 20;
	boolean male = true;
	double height = 1.8 ;
	Address home = new Address(); 
	
	public static class Address {
	    String street = "Main street", city = "New York";
	    int postcode = 10014;
	}

    public static void main(String[] args) {
        Person x = new Person();

        // Creates a POJO filter from an expression
        Filter filter = FilterParser.instance.parse("age > 18");
            
        if (filter.match(x)) // Checks if x matches the expression
            System.out.println(x.firstname+" "+x.name+" is more than 18 years old.");

        // Creates a POJO filter from the LDAP syntax
        Filter filter = FilterParser.instance.parse("(home.city=New York)");
            
        if (filter.match(x)) // Checks if x matches the LDAP filter
            System.out.println(x.firstname+"'s home city is "+x.home.city);

        // Creates a POJO filter from the JSON syntax
        Filter filter = FilterParser.instance.parse("{name:Doe}");
            
        if (filter.match(x)) // Checks if x matches the JSON filter
            System.out.println(x.firstname+"'s name is "+x.name);
    }
}
```

Currently, the library supports the following LDAP-like filters:

| Operator | Description  | Supported types | Filter example |
|:--------:|:------------:|:----------------| --------------:|
| `=`      | *equals to*  | [String](http://docs.oracle.com/javase/6/docs/api/java/lang/String.html), [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html), [Object](http://docs.oracle.com/javase/6/docs/api/java/lang/Object.html) | `(firstname = John)` |
| `~`      | *differs from* | [String](http://docs.oracle.com/javase/6/docs/api/java/lang/String.html), [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html), [Object](http://docs.oracle.com/javase/6/docs/api/java/lang/Object.html) | `(name ~ Smith)` |
| `>`      | *more than*  | [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html) | `(height > 1.6)` |
| `>=`     | *more or equals*  | [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html) | `(height >= 1.6)` |
| `<`      | *less than*  | [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html) | `(age < 20)` |
| `<`      | *less or equals*  | [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html) | `(age <= 20)` |
| `!`      | *not*        | Filter          | `!(age<10)` |
| `&`      | *and*        | Filters         | `&(name=Doe)(firstname=John)` |
| `PIPE`   | *or*         | Filters         | `PIPE(age<10)(male=true)` |

The library also supports the following JSON-like filters:

| Operator | Description  | Supported types | Filter example |
|:--------:|:------------:|:----------------| --------------:|
| `:`      | *equals to*  | [String](http://docs.oracle.com/javase/6/docs/api/java/lang/String.html), [Number](http://docs.oracle.com/javase/6/docs/api/java/lang/Number.html), [Object](http://docs.oracle.com/javase/6/docs/api/java/lang/Object.html) | `{firstname:John}` |
| `,`      | *and*        | Filters         | `{name:Doe,firstname:John}` |



## Licence

    Copyright (C) 2012 University Lille 1, Inria

    This library is free software; you can redistribute it and/or modify
    it under the terms of the GNU Library General Public License as published
    by the Free Software Foundation; either version 2 of the License, or (at
    your option) any later version.

    This library is distributed in the hope that it will be useful, but WITHOUT
    ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
    FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Library General Public
    License for more details.

    You should have received a copy of the GNU Library General Public License
    along with this library; if not, write to the Free Software Foundation,
    Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301, USA.