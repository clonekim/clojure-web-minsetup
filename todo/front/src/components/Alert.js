import React from 'react';


function Alert ({ errors, clearError }) {

    if(errors)
        console.log('Erros:', errors);

    if(errors) {
        return (
            <div style={{border: '1px solid', width: 400}}>
            <h1 style={{color: 'red'}}>Error</h1>
             <button onClick={clearError}>x</button>
            { Object.keys(errors).map((k,v) => <p key={v}>{k}: {errors[k]}</p> )}
            </div>
        );
    }else {
        return null;
    }


}


export default Alert;
