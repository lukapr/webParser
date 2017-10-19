import React, {Component, PropTypes} from 'react';

import JqxNotification from '../../assests/jqwidgets-react/react_jqxnotification.js';

export class ErrorNotification extends Component {

    constructor(props) {
        super(props);
        this.state={message: ""};
    }

    handleOpen = (el) => {
        this.setState({
            message: el
        });
        this.refs.errorNotificationRef.open();
    }

    render() {
        return (
            <JqxNotification ref='errorNotificationRef'
                             width={'auto'} height={'auto'}  opacity={1} autoCloseDelay={3000} animationOpenDelay={800}
                             autoClose={true} autoOpen={false} position={'bottom-right'} template={'error'}
            >
                <div style={{fontSize: '130%'}}> {this.state.message} </div>
            </JqxNotification>
        );
    }
}

export class SuccessNotification extends Component {
    constructor(props) {
        super(props);
        this.state={message: ""};
    }

    handleOpen = (el) => {
        this.setState({
            message: el
        });
        this.refs.successNotificationRef.open();
    };

    render() {
        return (
            <JqxNotification ref='successNotificationRef'
                             width={'auto'} height={'auto'}  opacity={1} autoCloseDelay={3000} animationOpenDelay={800}
                             autoClose={true} autoOpen={false} position={'bottom-right'} template={'info'}
            >
                <div style={{fontSize: '130%'}}> {this.state.message} </div>
            </JqxNotification>
        );
    }
}


