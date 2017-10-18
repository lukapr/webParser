import React, {Component, PropTypes} from 'react';
import NewConfigButton from "./newConfigForm.jsx";
import ConfigTable from "./configTable.jsx";
import {notify} from 'react-notify-toast';

import request from 'superagent';

export default class ConfigsInfo extends Component {
    constructor(props) {
        super(props);
        this.state = {
            configs: []
        };
    };

    loadConfigsFromServer = () => {
        request.get("configs/")
            .end((err, res) => {
                if (err || !res.ok) {
                    notify.show('Error during getting configs: ' + err, "error");
                } else {
                    // notify.show('yay got ' + JSON.stringify(res.body), "success");
                    this.setState({configs: res.body})
                }
            })
    };

    componentWillReceiveProps(nextProps) {
        if (nextProps.load) {
            this.loadConfigsFromServer();
        }
    };

    render() {
        return ( <div>
            <ConfigTable configs={this.state.configs} onEdit={this.loadConfigsFromServer}/>
            <div style={{width: '100%', paddingTop: '10px', paddingBottom: '10px'}}>
                <NewConfigButton onClose={this.loadConfigsFromServer} title={"Add new config"}/>
            </div>
        </div>)
            ;
    }
}
ConfigsInfo.propTypes = {
    load: PropTypes.bool
}