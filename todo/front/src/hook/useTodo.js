import { useState, useEffect } from 'react';
import api from '../service/todos';


export default function useTodo() {

    const [todos, setTodos] = useState([]);
    const [errors, setErrors] = useState();


    useEffect(() => {
        console.log('run useEffect');
        api.getTodos().then(todos =>  {
            setTodos(todos)
            setErrors(null);
        });
    }, []);


    const deleteTodo = id => {
        console.log('deleteTodo by ', id);
        api.deleteTodo(id).then(() => {
            const list = todos.filter(todo => todo.id !== id);
            setTodos(list)
            setErrors(null);
        }).catch(err => {
            setErrors({message: '해당 Todo가 없습니다'});
        });
    };


    const addTodo = (todo) => {
        console.log('addTodo');
        return api.addTodo(todo).then( addedTodo => {
            setTodos(todos.concat(addedTodo));
            setErrors(null);
        }).catch(err => {
            setErrors(err.response.data.errors);
        });
    };


    const clearError = () => setErrors(null);

    return {
        todos, deleteTodo, addTodo, errors, clearError
    }
}
