package forum.board.service;

import forum.board.domain.Item;
import forum.board.repository.MemoryBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final MemoryBoardRepository memoryBoardRepository;

    public List<Item> getFreeBoard()
    {
       return memoryBoardRepository.findAll();
    }



}
