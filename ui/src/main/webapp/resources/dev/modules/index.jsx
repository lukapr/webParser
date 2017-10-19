import React, {Component} from 'react';
import {Tabs, Tab} from 'material-ui/Tabs';
import ConfigsInfo from './configsInfo.jsx'
import Results from './results.jsx'
import Products from './products.jsx'
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';

const styles = {
    headline: {
        fontSize: 24,
        paddingTop: 16,
        marginBottom: 12,
        fontWeight: 400,
    },
};

export default class Index extends Component {

    constructor(props) {
        super(props);
        this.state = {
            value: 'home',
        };
    }

    handleChange = (value) => {
        this.setState({
            value: value,
        });
    };

    render() {
        return (<MuiThemeProvider>
            <div style={{width: '80%', margin: 'auto'}}>
                <Tabs
                    value={this.state.value}
                    onChange={this.handleChange}
                >
                    <Tab label="Home" value="home">
                        <h2>Home!!!</h2>
                    </Tab>
                    <Tab label="Configs" value="configs">
                        <ConfigsInfo load={this.state.value === "configs"}/>
                    </Tab>
                    <Tab label="Result" value="results">
                        <Results load={this.state.value === "results"}/>
                    </Tab>
                    <Tab label="Products" value="products">
                        <Products load={this.state.value === "products"}/>
                    </Tab>
                </Tabs>
            </div>
        </MuiThemeProvider>)
            ;
    }

}