# Email Service

This is an email service that is using third-party email providers to send emails. It is currently supporting two email providers (Mailgun and Sendgrid).

It was designed to automatically failover to the second email provider should the request to the first provider fails.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. 

### Prerequisites

It is important that your environment has the following:
jdk8 or higher (As of writing this, the latest version is jdk1.8.0_181)
maven 3.x.x (As of writing this, the latest version is 3.5.4)

Third Party Email Providers
1. Mailgun - Create an account on https://www.mailgun.com/ and note the api user and key by following this instructions:
https://help.mailgun.com/hc/en-us/articles/203380100-Where-can-I-find-my-API-key-and-SMTP-credentials-

2. SendGrid - Create an account on https://sendgrid.com/ and note the api user and key by following this instructions:
https://sendgrid.com/docs/User_Guide/Settings/api_keys.html

##
The application requires encrypted keys. The application will decrypt these keys on runtime before using them.

An encryption utility has been built using Jasypt. 

Simply find this class from the test folder:
```
com.au.siteminder.encryption.JasyptEncrypterTest
```

Update the value of this field with the key that you got from Mailgun and Sendgrid
```
//Edit this with the sensitive information that you want to encrypt
    private static final String YOUR_KEY = "put_your_key_here";
```

Run this method

```
/* Use this method to encrypt your keys */
    @Test
    public void encryption_tool(){
        String encryptedKey = JasyptEncrypter.encrypt(YOUR_KEY);
        System.out.println(encryptedKey);
        System.out.println(JasyptEncrypter.decrypt(encryptedKey));
    }
```

The encrypted and non encrypted key will be printed out in the console. Take note of the encrypted key and we will use that later as one of the environment variables for the application.
##

By this time, you will have the domain url of your Mailgun and Sendgrid instance. You will also have your api user and encrypted api key.

You need to set the following as environment variables:
```
MAILGUN_USER
MAILGUN_KEY
MAILGUN_URI
SENDGRID_USER
SENDGRID_KEY
SENDGRID_URI
```

There are multiple ways of doing this.

1. Set them as one of your linux/windows environment variables
2. If you're planning to run the application using an IDE, find the FAQs page of your IDE on how to set environment variables.
3. If you're using mvn spring-boot:run to run the application, you can skip this step and just run the command with the environment variables:
```
mvn spring-boot:run -DMAILGUN_USER=<Your mailgun username> -DMAILGUN_KEY=<Your mailgun api key> -DSENDGRID_USER=<Your sendgrid username> -DSENDGRID_KEY=<Your encrypted sendgrid apikey> -<URI of your Mailgun Instance> -DSENDGRID_URI=<URI of your SendGrid Instance>
```

### Installing and Running the Application

1. Clone the repository to your local machine

2. Run this command to download the dependencies and compile the classes:
```
mvn clean install
```

3.a. Using your favorite IDE, locate the main application class and run it as application: 
```
SmEmailServiceApplication
```
3.b. In your command prompt or unix console, run this command:
```
mvn spring-boot:run -DMAILGUN_USER=<Your mailgun username> -DMAILGUN_KEY=<Your mailgun api key> -DSENDGRID_USER=<Your sendgrid username> -DSENDGRID_KEY=<Your encrypted sendgrid apikey> -<URI of your Mailgun Instance> -DSENDGRID_URI=<URI of your SendGrid Instance>
```

Access the swagger url to check whether the application ran successfully:
http://localhost:8080/email/swagger-ui.html


## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Author

* **Wataru Nakayama** - (https://github.com/watarucarlo)
