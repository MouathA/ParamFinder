# ParamFinder

ParamFinder is a powerful tool designed to extract URLs with parameters efficiently, enabling users to identify and analyze web resources with parameterized URLs.

> Parameters in URLs can reveal important information and potential vulnerabilities in web applications, making them a critical aspect of security testing and analysis.

## Summary

- [Usage](#usage)
- [Installation](#installation)
- [Examples](#examples)
- [Blacklist](#blacklist)
- [Recommended](#recommended)

## Usage

```
Usage: java -jar ParamFinder.jar -d <domain> -o <output_file_path> [-p]

Options:
  -d, --domain <domain>        Specify the domain to scan
  -o, --output <output_file_path>  Specify the output file path
  -p, --params                 Extract URLs with parameters only
```

## Installation

You can download the latest release of ParamFinder from the [GitHub repository](https://github.com/MouathA/ParamFinder/releases).

## Examples

> To extract URLs with parameters from a domain and save them to a file:

```sh
java -jar ParamFinder.jar -d example.com -o output.txt
```

> To extract only URLs with parameters:

```sh
java -jar ParamFinder.jar -d example.com -o output.txt -p
```

## Blacklist

ParamFinder skips URLs with the following extensions:

```
.jpg, .jpeg, .png, .gif, .pdf, .svg, .json,
.css, .js, .webp, .woff, .woff2, .eot, .ttf, .otf, .mp4, .txt
```

## Recommended

> Use Java 11 or higher to ensure compatibility and optimal performance.
```
