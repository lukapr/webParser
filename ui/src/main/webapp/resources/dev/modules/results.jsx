import React, {Component, PropTypes} from 'react';

import JqxBarGauge from '../../assests/jqwidgets-react/react_jqxbargauge.jsx';
import request from 'superagent';
import {ErrorNotification} from "./notifications.jsx";

export default class Results extends Component {
    constructor(props) {
        super(props);
        this.state = {
            keys: [],
            values: []
        };
    }

    //
    componentWillReceiveProps(nextProps) {
        if (nextProps.load) {
            this.loadFromServer();
        }
    };

    //
    loadFromServer = () => {
        request.get("results/orders")
            .end((err, res) => {
                if (err || !res.ok) {
                    this.refs.errorNotification.handleOpen('Error during getting orders info: ' + err);
                } else {
                    // notify.show('yay got ' + JSON.stringify(res.body), "success");
                    let keys = [];
                    let values = [];
                    for (let k in res.body) {
                        if (res.body.hasOwnProperty(k)) {
                            keys.push(k);
                            values.push(res.body[k])
                        }
                    }
                    this.setState({
                        keys: keys,
                        values: values
                    });
                    this.refs.myBarGauge.val(this.convertToArray(this.state.values));
                }
            })
    };

    convertToArray(items) {
        let preparedArray = new Array(items.length);
        for (let i = 0; i < items.length; i += 1) {
            preparedArray[i] = items[i];
        }
        return preparedArray;
    };

    render() {
        return (
            <div>
                <ErrorNotification ref='errorNotification'/>
                <JqxBarGauge ref='myBarGauge'
                             width={600} height={600} colorScheme={'scheme01'}
                             max={5000} values={[0, 100]}
                />
            </div>

        )
    }

}