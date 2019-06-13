# DFLib Documentation Project

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

TODO: publish with Travis 