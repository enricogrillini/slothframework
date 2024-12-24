# Sloth framework

## Riferimenti

- [Doc github](https://docs.github.com/en/free-pro-team@latest/packages/guides/configuring-apache-maven-for-use-with-github-packages)

## Installazione su git hub

```shell

# Check update
mvn versions:display-dependency-updates

# Deploy 
git checkout master
git pull
mvn deploy
git checkout develop
```

## Prerequisiti

Per poter utilizzare gli artefatti del framework è necessaria la seguente configurazione maven (file `.m2/settings.xml`).

**Nota: sostituire `###USERNAME###` `###TOKEN###` con le proprie credenziali git hub**

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                      http://maven.apache.org/xsd/settings-1.0.0.xsd">

 <activeProfiles>
  <activeProfile>github</activeProfile>
 </activeProfiles>

 <profiles>
  <profile>
   <id>github</id>
   <repositories>
    <repository>
     <id>central</id>
     <url>https://repo1.maven.org/maven2</url>
     <releases>
      <enabled>true</enabled>
     </releases>
     <snapshots>
      <enabled>true</enabled>
     </snapshots>
    </repository>
    <repository>
     <id>github-sloth-framework</id>
     <name>GitHub enricogrillini Apache Maven Packages</name>
     <url>https://maven.pkg.github.com/enricogrillini/sloth-framework</url>
    </repository>
   </repositories>
   <pluginRepositories>
    <pluginRepository>
     <id>github-sloth-plugin</id>
     <name>GitHub enricogrillini Apache Maven Packages</name>
     <url>https://maven.pkg.github.com/enricogrillini/sloth-plugin</url>
    </pluginRepository>
   </pluginRepositories>
  </profile>
 </profiles>

 <servers>
  <server>
   <id>github-sloth-framework</id>
   <username>###USERNAME###</username>
   <password>###TOKEN###</password>
  </server>
  <server>
   <id>github-sloth-plugin</id>
   <username>###USERNAME###</username>
   <password>###TOKEN###</password>
  </server>
 </servers>
</settings>
```


