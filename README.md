# DFLib Documentation Project

## Local Setup

After clone, run this to ensure IPython notebooks are committed without output data:

```
git config --local include.path ../.gitconfig
```

## Published Site Location

https://dflib.org/

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