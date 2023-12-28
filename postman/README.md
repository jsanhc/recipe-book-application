# Postman collection integration tests
## Requisites
- Postman
```shell
  Version 10.8.8
  UI Version: 10.8.8-ui-230201-0452
  Desktop Platform Version: 10.8.0 (10.8.0)
```
- Download postman from https://www.postman.com/downloads/
- Install postman
- Import into postman app the below files:
  - Collection: `[LOCAL] Recipe book application.postman_collection.json`
  - Environment: `Local dev.postman_environment.json`

> To import files to postman: click in the import button in the top left sidebar or go to `files/import`

## Run Postman collection
> Make sure the application is running in your local environment
- Select the collections in the left sidebar.
- Select the environment `Local dev` in the right top sidebar or select it in the environments left sidevar
- In the collection name click on `...` and select `Run collection`
> Optionally you can run the requests manually. 