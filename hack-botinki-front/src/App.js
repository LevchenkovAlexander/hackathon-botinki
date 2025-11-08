import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { Panel, Flex } from '@maxhub/max-ui';
import Sidebar from "./components/Sidebar";
import Home from "./pages/Home";
import Page1 from "./pages/Page1";
import Page2 from "./pages/Page2";

const App = () => (
    <Router>
        <Panel mode="secondary" className="panel">
            <Flex direction="row" style={{ height: "100vh" }}>
                {/* Sidebar слева */}
                <Sidebar />
                
                {/* Основной контент справа */}
                <div style={{ flex: 1, padding: "20px", overflow: "auto" }}>
                    <Routes>
                        <Route path="/" element={<Home />} />
                        <Route path="/page1" element={<Page1 />} />
                        <Route path="/page2" element={<Page2 />} />
                    </Routes>
                </div>
            </Flex>
        </Panel>
    </Router>
)

export default App;
