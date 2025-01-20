# DFLib Documentation Project

## Local Setup

### Git
After clone, run this to ensure IPython notebooks are committed without output data:

```
git config --local include.path ../.gitconfig
```

To build site content you need to install [Hugo](https://gohugo.io/installation/) manually. As of this writing Hugo version is
 [v0.139.0](https://github.com/gohugoio/hugo/releases/tag/v0.139.0).

### Local Web Server

Since the site template uses absolute paths, it will not work properly if you simply open `index.html` in the browser.
To test the site, you should run a webserver. We are providing a `docker-compose.yml` file to start a server pointing
to the docs dir. Run the following command and go to http://127.0.0.1:11080/ 

```
docker-compose up -d
```

## Project Structure

* `docs` : assembled docs are placed here to be deployed on GitHub Pages
* `<submodule>/src/docs/asciidoc` : Asciidoc source folders
* `<submodule>/src/test/java` : Java examples embedded in the documentation

## Publishing the docs

```
mvn clean package
git add -A
git commit -m 'Regenerating docs'
git push
```

If the commit is to the `main` branch, the push will activate publishing of the website at
https://dflib.org/ .