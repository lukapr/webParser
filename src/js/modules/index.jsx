import React, {Component} from 'react';
import Notifications from 'react-notify-toast';
import {Tabs, Tab} from 'material-ui/Tabs';
import ConfigsInfo from './configsInfo'

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
        return ( <div style={{width: '60%', margin: 'auto'}}>
            <Notifications/>
            <Tabs
                value={this.state.value}
                onChange={this.handleChange}
            >
                <Tab label="Home"  value="home">
                    <h2>Home</h2>
                </Tab>
                <Tab label="Configs" value="configs">
                    <ConfigsInfo load={this.state.value === "configs"}/>
                </Tab>
            </Tabs>
        </div>)
            ;
    }

}