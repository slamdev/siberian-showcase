# API

## Upload file

```
POST /rest/hl7 HTTP/1.1
Content-Type: multipart/form-data
Content-Length: number_of_bytes_in_entire_request_body

--foo_bar_baz
Content-Disposition: form-data; name="inputFile"; filename="your_file_name"
Content-Type: text/plain

MSH|^~\&|||||20150618214940.752+0530||A01^ADT_A01|13101|T|2.5

```

## Get file content

```
GET /rest/hl7/{your_file_name}
```
