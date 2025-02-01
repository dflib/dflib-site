# DFLib Documentation Project

## Prerequisites

* After cloning the code locally, run the following command to ensure IPython notebooks are committed without output 
data:
```
git config --local include.path ../.gitconfig
```

* Install [Hugo](https://gohugo.io/installation/), version 0.139.0 or newer. On MacOS this should be as easy as 
`brew install hugo`.

### Local Web Server

Since the site template uses absolute paths, it will not work properly if you simply open `index.html` in the browser.
To test the site, you should run a webserver. We are providing a `docker-compose.yml` file to start a server pointing
to the docs dir. Run the following command and go to http://127.0.0.1:11080/ 

```
docker-compose up -d
```

## Project Structure

* `charts` : Jupyter notebooks to generate website chart galleries
* `docs` : the final website assembly. Adding files here results in publishing them on dflib.org.
* `dflib1-docs` : DFLib Asciidoc documentation
* `hugo` : manually written webpages and CSS assembly
* `jjava1-docs` : JJava Asciidoc documentation

## Publishing the docs

```
mvn clean package
git add -A
git commit -m 'Regenerating docs'
git push
```

If the commit is to the `main` branch, the push will activate publishing of the website at
https://dflib.org/ .