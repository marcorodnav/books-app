#!/bin/bash

create_container_with_db='docker run -p 5432:5432 --name booksapp_postgres -e POSTGRES_USER=booksapp_user -e POSTGRES_PASSWORD=booksapp_pass -e POSTGRES_DB=booksapp -d postgres'
eval $create_container_with_db
