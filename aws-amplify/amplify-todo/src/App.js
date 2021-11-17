import {useEffect, useState} from "react";
import {API, graphqlOperation} from "aws-amplify";
import {listTodos} from "./graphql/queries";
import {createTodo, deleteTodo} from "./graphql/mutations";
import {v4 as uuid} from "uuid"

function App() {
    const [todos, setTodos] = useState([]);
    const [newTodo, setNewTodo] = useState("");
    const [description, setDescription] = useState("");

    useEffect(() => {
        initData();
    }, [])

    const initData = async () => {
        const response = await API.graphql(graphqlOperation(listTodos))
        console.log(response);
        setTodos(response.data.listTodos.items)
    };

    const submitTodo = async () => {
        const response = await API.graphql(graphqlOperation(createTodo, {
            input: {
                id: uuid(),
                name: newTodo,
                description
            }
        }))
        console.log(response);
        initData()
    }

    const deleteExistTodo = async (id) => {
        try {
            await API.graphql(graphqlOperation(deleteTodo, {input : {id}}))
            setTodos([...todos].filter(todo => todo.id !== id))
        } catch (e) {
            console.log(e)
        }
    }

    return (
        <div>
            <input type={"text"} onChange={(e) => setNewTodo(e.target.value)} placeholder={"제목"}/>
            <input type={"text"} onChange={(e) => setDescription(e.target.value)} placeholder={"내용"}/>
            <button onClick={submitTodo}>등록</button>
            <div>
                {todos.map(todo => <div key={todo.id}>{todo.name}, {todo.description}
                    <button onClick={() => deleteExistTodo(todo.id)}>삭제</button>
                </div>)}
            </div>
        </div>
    );
}

export default App;
