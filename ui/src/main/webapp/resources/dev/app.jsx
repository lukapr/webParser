import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import Index from './modules/index.jsx'


ReactDOM.render(<Index/>, document.getElementById('root'));

if (module.hot) {
    module.hot.accept('./modules/index.jsx', () => {
        const NextIndex = require('./modules/index.jsx').default;
        render(<Index />, document.getElementById('root'));
    })
}