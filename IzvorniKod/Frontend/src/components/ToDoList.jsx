import React from 'react'
import Todo from './ToDo'

export default function TodoList({ todos, toggleTodo }) {
 // console.log("aaaaaaaaaaaaaaaaaaa", todos)
  return (
    <>
      {
        todos.map(todo => {
          return <Todo key={todo.workoutId} toggleTodo={toggleTodo} todo={todo} />
        })
      }
    </>
  )
}