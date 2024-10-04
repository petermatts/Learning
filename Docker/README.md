# Docker

This is the code where I am learning to use Docker :)

Tutorial link to follow: https://docker-curriculum.com/

### Llama - Side note

Get the container: `docker run --gpus=all -d -v ollama:/root/.ollama -p 11434:11434 --name ollama ollama/ollama`

Run the container: `docker exec -it ollama ollama run llama2`