import React, {Component} from 'react';
import NewConfigForm from "./newConfigForm";
import ConfigTable from "./configTable";
import Notifications, {notify} from 'react-notify-toast';
import {GridList} from 'material-ui/GridList';

import request from 'superagent';

const styles = {
    root: {
        display: 'flex',
        flexWrap: 'wrap',
        justifyContent: 'space-around',
    },
    gridList: {
        width: 500,
        height: 450,
        overflowY: 'auto',
    },
};

export default class Index extends Component {
    constructor(props) {
        super(props);
        this.state = {
            configs: []
        };
        this.loadConfigsFromServer = this.loadConfigsFromServer.bind(this);
        // this.componentDidMount().bind(this);
    };

    loadConfigsFromServer = () => {
        request.get("configs/")
            .end((err, res) => {
                if (err || !res.ok) {
                    notify.show('Error during getting configs: ' + err, "error");
                    this.setState({
                        configs: [{id: "1", name: "name", description: "desc", link: "link"},
                            {id: "2", name: "name11", description: "desc", link: "link"}]
                    });
                } else {
                    // notify.show('yay got ' + JSON.stringify(res.body), "success");
                    this.setState({configs: res.body})
                }
            })
    };

    componentDidMount() {
        this.loadConfigsFromServer();
    };

    render() {
        return ( <div style={{width: '60%', margin: 'auto'}}>
            <Notifications/>
            <ConfigTable configs={this.state.configs} onEdit={this.loadConfigsFromServer}/>
            <GridList
                padding={-20}
            />
            <NewConfigForm onClose={this.loadConfigsFromServer} title={"Add new config"}/>
        </div>)
            ;
    }
}