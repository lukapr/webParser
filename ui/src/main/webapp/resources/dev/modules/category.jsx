import React, {Component, PropTypes} from 'react';

import request from 'superagent';

import JqxRibbon from '../../assests/jqwidgets-react/react_jqxribbon.js';

export default class Category extends Component {
    constructor(props) {
        super(props);
        this.state = {
            categories: []
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
        request.get("results/categories")
            .end((err, res) => {
                if (err || !res.ok) {
                    // this.refs.errorNotification.handleOpen('Error during getting categories: ' + err);
                } else {
                    this.setState({
                        categories: res.body
                    });
                }
            })
    };

    render() {
        return (<JqxRibbon ref='myRibbon'
                           width={800}  height={120}
                           selectionMode={'click'} selectedIndex={1}>
                <ul>
                    <li>Item 1</li>
                    <li>Item 2</li>
                    <li>Item 3</li>
                    <li>Item 4</li>
                </ul>
                <div>
                    <div>Content 1</div>
                    <div>Content 2</div>
                    <div>Content 3</div>
                    <div>Content 4</div>
                </div>
            </JqxRibbon>
        )
    }

}