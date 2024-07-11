# Java Google Cloud Platform Microservice
Spring-Boot application microservice that interacts with Google Cloud Platform's API to automate the provisioning of compute VMs instances.

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)


## Features
This microservice provides the following:

* Spring-Boot Application with Spring Security
* Google Authorization from File
* Provisioning and Deprovisioning instances with given instance ID
* Managing instance lifecycle (start, stop, restart)

## Requirements
The application can be run locally, the requirements for each setup are listed below.

### GCP
A running instance of [GCP](https://github.com/cemsagay/gcp.git) is required to generate the Authorization tokens and to provide the verification key.

[GCP](https://github.com/cemsagay/gcp.git) can be cloned and run locally if a public instance is not setup.


### Local
* [Java 22 SDK](https://www.oracle.com/java/technologies/downloads)
* [Maven](https://maven.apache.org/download.cgi)


## Quick Start
Make sure the Google Credentials JSON file is configured, then you can run the server in a docker container or on your local machine.


### Configure Google Cloud Credentials
Update __gcp-service-account.json__.

Here is a convenient list of steps to create a new application based on this template:


1. Login in to [Google Cloud Platform](https://console.cloud.google.com/).
2. Create a service account.
3. Open the service account in your cloud console and add a key
4. In the dropdown menu choose create key 4.Then choose key type json
4. Download the file and rename it to  __gcp-service-account.json__:
5. Replace the existing file in resources folder.

The default value in the __application.properties__ file is set to connect to GCP running locally on its default port `8080`.

### Run Local
```bash
$ mvn spring-boot:run
```

Application will run by default on port `8080`

Configure the port by changing `server.port` in __application.properties__

### Test Endpoint
The application has a single endpoint `/gcp` that will accept POST, PUT and DELETE requests with a valid parameters.

Test cURL requests 
(Change the parameters `zone`,`projectId`,`instanceId` with the real values):

Create an instance with ID
 ```bash
curl -X POST localhost:8080/gcp/[zone]/[projectId]/[instanceId]
```
Restart an instance with ID
```bash
curl -X PUT localhost:8080/gcp/[zone]/[projectId]/[instanceId]
```
Start an instance with ID
```bash
curl -X POST localhost:8080/gcp/[zone]/[projectId]/[instanceId]/start
```
Stop an instance with ID
```bash
curl -X POST localhost:8080/gcp/[zone]/[projectId]/[instanceId]/stop
```
Delete an instance with ID
 ```bash
curl -X DELETE localhost:8080/gcp/[zone]/[projectId]/[instanceId]
```
In case response returns as 403 forbidden, you may need to assign your service account with "compute.instances.delete" permission on Google Cloud Platform.

If everything is working as expected, the request should return a pleasant response. :)