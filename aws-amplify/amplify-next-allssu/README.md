##  AWS Amplify GraphQL - 얼쑤 ALLSSU
https://www.youtube.com/watch?v=W0d9lGKZZtA

### SETUP
1. `yarn create next-app amplify-next-allssu` next js 프로젝트 생성 
2. 생성된 프로젝트 디렉토리에서 `yarn dev` 개발모드 진입
3. chakra ui 적용 `yarn add @chakra-ui/react @emotion/react@^11 @emotion/styled@^11 framer-motion@^4` (https://chakra-ui.com/docs/getting-started)

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
       <div}>
       </div>
     )
   }
   ```
4. 