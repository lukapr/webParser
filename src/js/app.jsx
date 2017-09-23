import React, {Component} from 'react';
import ReactDOM from 'react-dom';
import NewConfigForm from "./modules/newConfigForm";
import ConfigTable from "./modules/configTable";
import Notifications, {notify} from 'react-notify-toast';
import request from 'superagent';

class App extends Component {
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
                    this.setState({configs: [{name: "name11111111111111111111111111111111", description: "desc", link: "link"},
                    {name: "name", description: "desc", link: "link"}]});
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
        return ( <div className="component-app">
            <Notifications/>
            <ConfigTable configs={this.state.configs}/>
            <NewConfigForm onClose={this.loadConfigsFromServer}/>
        </div>);
    }
}

ReactDOM.render(<App/>, document.getElementById('root'));