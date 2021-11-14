##  AWS Amplify GraphQL - 얼쑤 ALLSSU


### 1. SETUP
https://www.youtube.com/watch?v=W0d9lGKZZtA
1. `yarn create next-app amplify-next-allssu` next js 프로젝트 생성 
2. 생성된 프로젝트 디렉토리에서 `yarn dev` 개발모드 진입
3. chakra ui 적용 https://chakra-ui.com/docs/getting-started
   
   `yarn add @chakra-ui/react @emotion/react@^11 @emotion/styled@^11 framer-motion@^4` (차크라 적용)
   
   `yarn add @chakra-ui/icons` (아이콘용 설치)

   ```js
   // pages/_app.js
   import '../styles/globals.css'
   import {ChakraProvider} from '@chakra-ui/react'
   
   function MyApp({Component, pageProps}) { return (
   <ChakraProvider>
   <Component {...pageProps} />
   </ChakraProvider>
   ); }
   
   export default MyApp
   
   ```
   ```js
   //page/index.js (next가 만들어준 다른 부분 다 삭제)
   export default function Home() {
     return (
       <div></div>
     )
   }
   ```
4. https://github.com/siyoon210/serverless/commit/6a9a047e3e05a705fed0a6f60b246af7d2ee38b4

### 2. GraphQL(Apsync)
https://www.youtube.com/watch?v=ly6uhz5SWiY

#### amplify 설치 & 설정
https://www.youtube.com/watch?v=Yz8DcuD0fKg
1. `npm install -g @aws-amplify/cli` amplify cli 설치 (글로벌이므로 없는 경우만 설치)
2. `amplify configure` 

#### API 설정
1. `amplify add api` (api 추가)
2. `amplify status` - push 할때 반영되는 리소스 상태를 보여줌
3. `amplify push`
4. `amplify console api` (aws api확인용 console 열기 - 테스트 할수 있는 환경)

### 3. React 앱에 Ampliify GraphQL 붙이기

1. `yarn add aws-amplify` 리액트 앱에서 amplify 사용하는 라이브러리 설치

https://github.com/siyoon210/serverless/commit/02c96b93475d7a7e7a64d9813c51d8d17e3c1129

### 4. 실시간 웹 (GraphQL Subscriptions)

1. schema.graphql clinetId 추가후 `amplify push`
2. `npm install uuid`