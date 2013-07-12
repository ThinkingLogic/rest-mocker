rest-mocker
===========

**REST Mocker** provides canned responses to REST requests, and allows you to decide *which* canned response to return based on matching one or more of:

*   The request method (GET, POST, PUT or DELETE)
*   Request Headers
*   Query Parameters
*   JSON Path expressions
*   XPath expressions

The intended usage is to replace (mock) a REST service for development and testing purposes - it allows you to develop and test against a known set of responses, and also test timeouts using a fixed or random delay.

## Requirements ##

1.  Apache Ant must be installed to build the application.
2.  The build task downloads dependencies from the internet, so must have access to the internet.

## How to build ##

1.  Download the zip file
2.  Extract it to your machine
3.  Open a command prompt in the extracted directory
4.  (If you are behind a firewall and need to specify proxy properties,
    create a local.properties file and populate the following: proxy.host, proxy.port, proxy.user, proxy.password)
5.  At the command prompt, type 'ant' (without quotes) and press return
6.  Follow the instructions to view rest-mocker in your browser

## How it works ##

The following text attempts to cover *all* of the options you have available, but you don't have to use any of them. All you have to do is create a default.body file containing the response you want in a file structure that matches the request path. That's it, really!
It's much easier to understand by seeing an example - so download it and give it a try!

On receiving an http request, the REST mocker will attempt to match the request to a set of resources on the classpath, looking for a 'path.properties' file in a directory matching the request path. If one is found, then mocker will attempt to find a sub-directory matching the properties of the request:

path.properties will be examined for any of the following properties, in order:

*   dir.method - (if 'dir.method=true') mocker will look in a subdirectory matching the request (GET, POST, PUT, DELETE)
*   dir.header - mocker will look in a subdirectory matching the specified request header. Note that there is special handling for the 'Accept' header, such that mocker will look for a subdirectory that matches any of the mime types in the Accept header, matching only the part after '/' (so a subdirectory of 'html' for an Accept header of 'text/html', etc).
*   dir.queryParam - mocker will look in a subdirectory matching the specified query parameter.
*   dir.jsonPath - (if the request has a json-formatted body) the json path expression will be evaluated, and mocker will look for a matching subdirectory
*   dir.xPath - (if the request has an xml-formatted body) the xpath expression will be evaluated, and mocker will look for a matching subdirectory

If a property is specified, and matches a value in the request, then no other properties will be considered - even if there is no matching subdirectory. Generally you should only specify one of these properties in any given path.properties file.

This process is repeated until no further subdirectories can be found (or until the first subdirectory without a path.properties is reached). If no path.properties file can be found matching the full request path, then mocker will walk back up the directory structure looking in each parent folder until it finds a path.properties file.

At this point, path.properties file will be examined for instructions to match a particular file, checking each of the following (in order) until a match is found:

*   file.method - (if 'file.method=true') mocker will look for a file matching the request (GET, POST, PUT, DELETE)
*   file.header - mocker will look for a file matching the specified request header. Note that there is special handling for the 'Accept' header, such that mocker will look for a file that matches any of the mime types in the Accept header, matching only the part after '/' (so a filename of 'html' for an Accept header of 'text/html', etc).
*   file.queryParam - mocker will look for a file matching the specified query parameter.
*   file.jsonPath - (if the request has a json-formatted body) the json path expression will be evaluated, and mocker will look for a matching file
*   file.xPath - (if the request has an xml-formatted body) the xpath expression will be evaluated, and mocker will look for a matching file

If a property is specified, and matches a value in the request, then no other properties will be considered - even if there is no matching file. Generally you should only specify one of these properties in any given path.properties file.

The filename will be used to look for 3 different files:

*   {filename}.body - the response body
*   {filename}.status - the http response code
*   {filename}.headers - the response headers

For any of the 3 types of file, if no file can be found with the matching name, then a default file from the current directory will be used instead (e.g. 'default.body'). If there is no default, then the parent directory will be examined for a matching filename, or default file, and so on.

There are a few other properties you can specify in path.properties (and you can specify default values for these in default.path.properties):

*   velocity - if true ('velocity=true'), then the response body is parsed as a velocity template before being returned
*   empty.value.replacement - if specified (e.g. 'empty.value.replacement=foo'), then any empty request property (not specified or empty string) will be replaced with this value when matching a directory or file. This can be very useful for differentiating between an empty value and one that is not recognised, but should be used with care. It is a very bad idea to put this in default.path.properties.
*   fixed.delay - the number of milliseconds to sleep before sending the response
*   random.delay - the maximum number of milliseconds to sleep before sending the response - the actual delay will be a random number between 0 and this number. Note that this is in addition to any fixed delay


