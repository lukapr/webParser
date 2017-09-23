import React, {Component} from 'react';
import NewConfigForm from "./newConfigForm";
import ConfigTable from "./configTable";
import Notifications, {notify} from 'react-notify-toast';
import {GridList, GridTile} from 'material-ui/GridList';
import IconButton from 'material-ui/IconButton';
import StarBorder from 'material-ui/svg-icons/toggle/star-border';

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
                        configs: [{name: "name11111111111111111111111111111111", description: "desc", link: "link"},
                            {name: "name", description: "desc", link: "link"}]
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
        <ConfigTable configs={this.state.configs}/>
            <GridList
                padding={-20}
            />
        <NewConfigForm onClose={this.loadConfigsFromServer}/>
    </div>)
        ;
    }
}