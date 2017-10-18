import React, {Component, PropTypes} from 'react';
import {notify} from 'react-notify-toast';

import request from 'superagent';

export default class Results extends Component {
    constructor(props) {
        super(props);
        this.state = {
            results: []
        };
    }

    componentWillReceiveProps(nextProps) {
        if (nextProps.load) {
            this.loadFromServer();
        }
    };

    loadFromServer = () => {
        request.get("results/")
            .end((err, res) => {
                if (err || !res.ok) {
                    notify.show('Error during getting configs: ' + err, "error");
                } else {
                    // notify.show('yay got ' + JSON.stringify(res.body), "success");
                    this.setState({results: res.body})
                }
            })
    };


    render() {
    }

}