import React from 'react';
import TodoList from './components/TodoList';
import TodoForm from './components/TodoForm';
import Alert    from './components/Alert';

import useTodo from './hook/useTodo';

function App() {

    const { todos, deleteTodo, addTodo, errors, clearError } = useTodo();

    return (
        <div className="App">
        <h1>Todo List</h1>
        <Alert errors={errors}  clearError={clearError}/>
        <TodoForm addTodo={ addTodo } />
        <TodoList
            todos={todos}
            deleteHandler={deleteTodo}>
        </TodoList>
        </div>
    );
}

export default App;
