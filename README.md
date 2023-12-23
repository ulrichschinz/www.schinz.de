# www

App to drive https://www.schinz.de

## Installation

Download from https://github.com/ulrichschinz/www.schinz.de

## Usage

### PROD

Follow the steps:  
* Build app
* Build docker container
* Deploy

#### build app
**Vanilla build**

```bash
clojure -T:build ci
```

**build with docker**

jar can be built with docker as well.  

```bash
docker run -it --rm -v $PWD:/usr/src/app -w /usr/src/app clojure -T:build ci
```

#### build docker container

```bash
docker build -t wwwschinzde .
```

#### run for live

The is a docker-compose file in this repo. You can configure some options, which I did in a docker-compose.override.yml. At least you should configure following for the docker container to run:  

**Set environment to tell the app where the cheatsheets are**  

```yaml
environment:
  - WWW_CS_DIR=/opt/app/cheatsheets
```

This enviornment variable tells the app where to look for the cheatsheets. This one is required.  

**Set volume for cheatsheets**  

```yaml
volumes:
  - ${PWD}/volumes/cheatsheets:/opt/app/cheatsheets
```

The directory with the cheatsheets can be anywhere, this is just an example. E.g. it could be a symbolic link to the cheatsheets repository.  

##### Example docker-compose.override.yml

```yaml
---
version: '3'
services:
  www:
    networks:
      - proxynet
    volumes:
      - ${PWD}/volumes/cheatsheets:/opt/app/cheatsheets
    environment:
      - WWW_CS_DIR=/opt/app/cheatsheets
    labels:
      - "traefik.http.routers.service-http.entrypoints=web"
      - .... <snip>SOME TRAEFIK CONFIG LABELS</snip>
networks:
  proxynet:
    name: proxy
    external: true
```

### style sheets

The stylesheet `style.css` and `style.css.map` are built with bootstrap sass. It is kept in another repository:  
[stylesheets](https://github.com/ulrichschinz/stylesheets)

To edit styles you can edit the `scss/www-style.scss` and compile it.  
In that repository is a babashka task to deploy the css stuff here.  


#### build container

```bash
docker build -t de.schinz/www .
```

### DEV

Most important feature for development is the repl, here we go with some hints on that.  

#### REPL

```bash
clojure -M:repl/rebel
````

and also

```bash
clojure -M:repl/reloaded
```

### Build project

```bash
clojure -T:build ci
```


### Run

To find the cheatsheets, you have to add the cheatsheets as a environment variable `WWW_CS_DIR`. The app will list all markdown files (identified by `*.md`) in the directory given in `WWW_CS_DIR`.  

```bash
export WWW_CS_DIR="/opt/app/cheatsheets"
```

**docker-compose.yml**

The docker compose file already contains that configuration. It will mount `${PWD}/volumems/cheatsheets` in the docker container. So you would create a volumes directory and symlink your cheatsheets in there.  

Run that uberjar:

```bash
export WWW_CS_DIR="/opt/app/cheatsheets"
java -jar target/www-0.1.0-SNAPSHOT.jar`
```

## License

Copyright © 2023 Ulrich Schinz

Distributed under the Eclipse Public License version 1.0.

See [LICENSE](https://github.com/ulrichschinz/www.schinz.de/blob/main/LICENSE) file
