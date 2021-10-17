#!/bin/bash

echo 'Connecting to books-app database'

connect='docker exec -it  booksapp_postgres psql -U  booksapp_user booksapp'
eval $connect