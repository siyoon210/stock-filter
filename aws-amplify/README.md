## 명령어
- init                         Initializes a new project, sets up deployment resources in the cloud, and makes your project ready for Amplify.                              
- configure                    Configures the attributes of your project for amplify-cli, such as switching front-end framework and adding/removing cloud-provider plugins.
- push                         Provisions cloud resources with the latest local developments.                                                                               
- pull                         Fetch upstream backend environment definition changes from the cloud and updates the local environment to match that definition.             
- publish                      Executes amplify push, and then builds and publishes client-side application for hosting.                                                    
- serve                        Executes amplify push, and then executes the project's start command to test run the client-side application locally.                        
- status [<category> ...]      Shows the state of local resources not yet pushed to the cloud (Create/Update/Delete).                                                       
- status -v [<category> ...]   Shows the detailed verbose diff between local and deployed resources, including cloudformation-diff                                          
- delete                       Deletes all of the resources tied to the project from the cloud.                                                                             
- <category> add               Adds a resource for an Amplify category in your local backend                                                                                
- <category> update            Update resource for an Amplify category in your local backend.                                                                               
- <category> push              Provisions all cloud resources in a category with the latest local developments.                                                             
- <category> remove            Removes a resource for an Amplify category in your local backend.                                                                            
- <category>                   Displays subcommands of the specified Amplify category.                                                                                      
- mock                         Run mock server for testing categories locally.                                                                                              
- codegen                      Generates GraphQL statements(queries, mutations and eventHandlers) and type annotations.                                                     
- env                          Displays and manages environment related information for your Amplify project.                                                               
- console                      Opens the web console for the selected cloud resource.                                                                                       
- logout                       If using temporary cloud provider credentials, this logs out of the account.

where <category> is one of: notifications, analytics, api, auth, function, geo, hosting, interactions, predictions, storage, xr
