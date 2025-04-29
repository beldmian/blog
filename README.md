# beldmian/blog - the blog "which just works"

A high-performance, minimalist blog engine built with Clojure. This project aims to provide a simple, fast, and reliable blogging platform with a clean UI and excellent performance characteristics.

## Installation

### Prerequisites

- [Clojure](https://clojure.org/guides/getting_started) (1.12.0 or later)
- Java JDK (version 8 or later)

### Clone the Repository

```bash
git clone https://github.com/beldmian/blog.git
cd blog
```

## Running the Application

### Development Mode

To run the application in development mode:

```bash
# Using the Makefile
make run

# Or directly with Clojure
clj -M -m server.core
```

The application will be available at http://localhost:8080

### Building and Running as JAR

```bash
# Build the uberjar
make build-uberjar
# or
clj -M:uberjar

# Run the JAR
make run-jar
# or
java -cp target/blog.jar clojure.main -m server.core
```

### Using Docker

```bash
# Build the Docker image
docker build -t beldmian-blog .

# Run the container
docker run -p 8080:8080 beldmian-blog
```

## Project Structure

- `src/` - Source code
  - `server/` - Server-side code and routing
  - `blog/` - Blog functionality
  - `home/` - Home page
  - `cv/` - CV/Resume page
  - `ui/` - UI components and styling
  - `index/` - Layout and styling
- `resources/` - Static resources and Markdown content
  - `articles/` - Markdown files for blog articles
  - `public/` - Public assets (CSS, images, etc.)
- `Dockerfile` - Docker configuration
- `Makefile` - Build and run commands

## Performance

With logging disabled on MacBook Air M2 (16 GB RAM) I got these results from load testing:

```bash
$ wrk -t2 -c16 -d10s http://localhost:8080/article/yastation
Running 10s test @ http://localhost:8080/article/yastation
  2 threads and 16 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   115.56us  200.93us  11.52ms   98.81%
    Req/Sec    67.21k     6.42k   73.21k    93.07%
  1351087 requests in 10.10s, 4.81GB read
Requests/sec: 133771.60
Transfer/sec:    488.10MB
```

## Technologies Used

- **[Clojure](https://clojure.org/)**: The primary programming language
- **[Hiccup](https://github.com/weavejester/hiccup)**: HTML generation library
- **[Markdown-clj](https://github.com/yogthos/markdown-clj)**: Markdown processing
- **[Garden](https://github.com/noprompt/garden)**: CSS generation from Clojure
- **[Pohjavirta](https://github.com/metosin/pohjavirta)**: High-performance web server
- **[Reitit](https://github.com/metosin/reitit)**: Fast data-driven routing library

## Adding Content

### Adding a New Article

1. Create a new Markdown file in `resources/articles/`
2. Add the article metadata and reference in `src/blog/articles.clj`
3. The article will automatically appear in the blog listing

Example article entry in `articles.clj`:

```clojure
(def articles-list
  {"article-id"
     (Article.
       "Article Title"
       "Article description or summary"
       "MM/DD/YYYY"
       (mc/inline-resource "articles/article-filename.md"))})
```

## Customization

### Styling

The project uses Garden for CSS generation. Main styling files:

- `src/ui/colors.clj`: Color palette
- `src/index/styles.clj`: Global styles
- `src/ui/` directory: Component-specific styles

### Layout

The main layout is defined in `src/index/layout.clj`.

## Deployment

The project includes GitHub Actions workflow for building and publishing Docker images. See `.github/workflows/build.yml` for details.

## License

This project is open source and available under the [MIT License](LICENSE).

## Author

Created by [beldmian](https://github.com/beldmian) for beldmian.