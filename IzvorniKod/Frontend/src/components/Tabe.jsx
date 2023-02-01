import React from 'react'

export default function Tabe({ key1,key2,funkc,todo}) {
  function handleT() {
    funkc(key2,key1)
  }
  
  return (
    <div>
      <label>
        <input type="checkbox" checked={todo.complete} onChange={handleT} />
        {key1+' h'}
      </label>
    </div>
  )
}