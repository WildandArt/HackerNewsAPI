
# Web Service RESTful API for HackerNews Project

## This is a Restful API for a Mini Hacker News Site with curl:

### To Create a post:
    curl -X POST http://localhost:8080/posts \
    -H "Content-Type: application/json" \
    -d '{"content": "This is a new post"}'

### To Get Top posts:
    curl http://localhost:8080/posts

### To Update a post:
    curl -X PUT http://localhost:8080/posts/<ID> \
    -H "Content-Type: application/json" \
    -d '{"content": "Updated post content"}'


### To Upvote a post:
    curl -X POST http://localhost:8080/posts/<ID>/upvote

### To Downvote a post:
    curl -X POST http://localhost:8080/posts/<ID>/downvote




