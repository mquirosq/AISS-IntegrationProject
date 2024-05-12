# VideoMiner

VideoMiner is a data mining tool that loads, processes and analyzes information from multimedia content creators hosted on different platforms.

The application is divided in three different microservices:

- VimeoMiner: Service in charge of reading data from the platform Vimeo  from the Vimeo REST API and send it to VideoMiner. 

- YoutubeMiner: Service in charge of reading data from the platform Youtube  from the YouTube REST API and send it to VideoMiner.

- VideoMiner: Service in charge of storing the data in an H2 database and offering it to the outside world via a REST API so that other applications can query and analyze it.

## Usage

###VideoMiner API:
##### The VideoMiner API offers the following services (The documentation can be found [here](http://localhost:8080/swagger-ui/index.html#/channels/findAll_1)):
#### GET
- Retrieve Video by Id: 
```/videominer/videos/{videoId}```
- Retrieve User by Id: 
```/videominer/users/{userId}```
- Retrieve Comment by Id: 
```/videominer/comments/{commentId}```
- Retrieve Channel by Id: 
```/videominer/channels/{channelId}```
- Retrieve Caption by Id: 
```/videominer/captions/{captionId}```
- Retrieve all comments from a video: 
```/videominer/videos/{videoId}/comments```
- Retreive all captions from a video: 
```/videominer/videos/{videoId}/captions```
- Retrieve all Channels: ``` /videominer/channels ```
- Retrieve all videos from a channel: ``` /videominer/channels/{channelId}/videos ```
- Retrieve all Videos: ``` /videominer/videos  ```
- Retrieve all Users: ``` /videominer/users ```
- Retrieve all Comments: ``` /videominer/comments ```
- Retrieve the author from a comment: ``` /videominer/comments/{commentId}/user ```
- Retrieve all Captions: ``` /videominer/captions ```

#### POST
- Insert a Comment in a video: ``` /videominer/videos/{videoId}/comments ```
- Insert a Caption in a video: ``` /videominer/videos/{videoId}/captions ```
- Insert a Channel: ``` /videominer/channels ```
- Insert a Video in a Channel: ``` /videominer/channels/{channelId}/videos ``` 

#### PUT
- Update a Video: ``` /videominer/videos/{videoId} ```
- Update a User: ``` /videominer/users/{userId} ```
- Update a Comment: ``` /videominer/comments/{commentId} ```
- Update a Channel: ``` /videominer/channels/{channelId} ```
- Update a Caption: ``` /videominer/captions/{captionId} ```

#### DELETE
- Delete a Video: ``` /videominer/videos/{videoId} ```
- Delete a Comment: ``` /videominer/comments/{commentId} ```
- Delete a Channel: ``` /videominer/comments/{commentId} ```
- Delete a Caption: ``` /videominer/captions/{captionId} ```


### VimeoMiner API 
##### The vimeoMiner API offers the following services (The documentation can be found [here](http://localhost:8081/swagger-ui/index.html#/channels/findAll_1)):

#### GET
- Retrieve a channel: 
```/vimeoMiner/api/v1/channels/{channelId}```
- Retrieve comments from a video: 
```/vimeoMiner/api/v1/videos/{videoId}/comments```
- Retrieve captions from a video: 
```/vimeoMiner/api/v1/videos/{videoId}/captions```
- Retrieve videos from channel: 
```/vimeoMiner/api/v1/channels/{channelId}/videos```
### POST
- Create a channel: 
```/vimeoMiner/api/v1/channels/{channelId}```

###YouTubeMiner API
##### The YoutubeMiner API offers the following services (The documentation can be found [here](http://localhost:8082/swagger-ui/index.html#/)):

#### GET
- Retrieve a channel by Id:  ```/youTubeMiner/api/v1/channels/{channelId}```
- Retrieve videos from channel: ```/youTubeMiner/api/v1/channels/{channelId}/videos```
- Retrieve comments from video: ```/youTubeMiner/api/v1/videos/{videoId}/comments```
- Retrieve captions from video: ```/youTubeMiner/api/v1/videos/{videoId}/captions```
- Retrieve a User from comment: ```/youTubeMiner/api/v1/comments/{commentId}/user```

#### POST
- Create a channel: ```/youTubeMiner/api/v1/channels/{channelId}```

## Documentation

Each service has been documented following the OpenAPI Specification standard. This has been done with the use of the documentation generation tool Swagger, and can be accessed via the following links:

[VideoMiner Documentation](http://localhost:8080/swagger-ui/index.html#/channels/findAll_1)

[VimeoMiner Documentation](http://localhost:8081/swagger-ui/index.html#/channels/findAll_1)

[YoutubeMiner Documentation](http://localhost:8082/swagger-ui/index.html#/channels/findAll_1)

## Access Ports

The VideoMiner data mining tool makes use of three different port numbers, each for a different service:

##### VideoMiner: Port 8080

##### VimeoMiner: Port 8081

##### YoutubeMiner: Port 8082

## OAuth
The service has implemented Open Authorization (OAuth 2.0) for access delegation in the YoutubeMiner service, adding a layer of security to the server resources.

## Pagination, filters and sorting
The VideoMiner API offers pagination, filtering and sorting in all it's GET requests were a list of elements is retreived.
This is done through request parameters that can be added in the URI of the request.
