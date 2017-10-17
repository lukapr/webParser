import React, {Component, PropTypes} from 'react';
import NewConfigButton from "./newConfigForm";
import ConfigTable from "./configTable";
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
                    this.setState({
                        configs: [{id: "1", name: "name", description: "desc", link: "link"},
                            {id: "2", name: "name11", description: "desc", link: "link"},
                            {id: "3", name: "name11", description: "desc", link: "link"},
                            {id: "4", name: "name11", description: "desc", link: "link"},
                            {id: "5", name: "name11", description: "desc", link: "link"},
                            {id: "6", name: "name11", description: "desc", link: "link"},
                            {id: "7", name: "name11", description: "desc", link: "link"},
                            {id: "8", name: "name11", description: "desc", link: "link"},
                            {id: "9", name: "name11", description: "desc", link: "link"},
                            {id: "10", name: "name11", description: "desc", link: "link"},
                            {id: "11", name: "name11", description: "desc", link: "link"},
                            {id: "12", name: "name11", description: "desc", link: "link"}
                        ]
                    });
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