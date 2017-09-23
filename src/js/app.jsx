import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import Index from './modules/index'
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';

class App extends Component {
    constructor(props) {
        super(props);
    };

    render() {
        return ( <MuiThemeProvider>
            <Index/>
        </MuiThemeProvider>);
    }
}

ReactDOM.render(<App/>, document.getElementById('root'));