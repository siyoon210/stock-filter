import {Center, Box, Text, Input, Button, List, ListItem, ListIcon} from '@chakra-ui/react'
import {CheckCircleIcon} from "@chakra-ui/icons";

export default function Home() {
    return (
        <Center>
            <Box>
                <Center>
                    <Text color={"teal"} fontWeight={"bold"} fontSize={"2xl"}>
                        Amplify GraphQL TodoList 만들기
                    </Text>
                </Center>
                <form>
                    <Box mt={2}>
                        <Input name={"subject"} placeholder={"제목"} onChange={null} value={null}/>
                        <Input name={"description"} placeholder={"내용"} onChange={null} value={null}/>
                    </Box>
                    <Center>
                        <Box mt={2}>
                            <Button colorScheme={"teal"} type={"submit"}>
                                등록
                            </Button>
                        </Box>
                    </Center>
                </form>
                <List spacing={3}>
                    <ListItem>
                        <Box fontWeight={"semibold"}>
                            <ListIcon as={CheckCircleIcon} color={"teal.500"}/>
                            할일 제목이 들어간다.
                        </Box>
                        <Box color={"gray.500"}>할일 내용이 들어간다. </Box>
                    </ListItem>
                    <ListItem>
                        <Box fontWeight={"semibold"}>
                            <ListIcon as={CheckCircleIcon} color={"teal.500"}/>
                            할일 제목이 들어간다.
                        </Box>
                        <Box color={"gray.500"}>할일 내용이 들어간다. </Box>
                    </ListItem>
                </List>
            </Box>
        </Center>
    )
}
