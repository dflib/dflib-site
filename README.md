# DFLib Documentation Project

## Local Setup

After clone, run this to ensure IPython notebooks are committed without output data:

```
git config --local include.path ../.gitconfig
```

To build site content you need to install [Hugo](https://gohugo.io/installation/) manually. As of this writing Hugo version is
 [v0.139.0](https://github.com/gohugoio/hugo/releases/tag/v0.139.0).

## Published Site Location

https://dflib.org/

## Project Structure

* `docs` : assembled docs are placed here to be deployed on GitHub Pages
* `src/main/asciidoc` : Asciidoc source folders
* `src/main/java` : Java examples embedded in the documentation

## Publishing the docs

```
mvn clean package
git add -A
git commit -m 'Regenerating docs'
git push
```

If the commit is to the `main` branch, the push will activate publishing of the website at
https://dflib.org/ .